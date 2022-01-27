package com.srk.api.junit

import com.srk.api.module
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import io.ktor.utils.io.streams.*
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class UploadFile {
    @Test
    fun `Upload binary file`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Post, "/api/upload") {
            val boundary = "WebAppBoundary"
            val fileBytes = File("ktor_logo.png").readBytes()

            addHeader(HttpHeaders.ContentType,
                ContentType.MultiPart.FormData.withParameter("boundary", boundary).toString())
            setBody(boundary, listOf(
                PartData.FormItem("ktor logo", {}, headersOf(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Inline
                        .withParameter(ContentDisposition.Parameters.Name, "description")
                        .toString()
                )),
                PartData.FileItem({ fileBytes.inputStream().asInput() }, {}, headersOf(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.File
                        .withParameter(ContentDisposition.Parameters.Name, "image")
                        .withParameter(ContentDisposition.Parameters.FileName, "ktor_logo.png")
                        .toString()
                ))
            ))
        }) {
            assertEquals("ktor logo is uploaded to 'uploads/ktor_logo.png'", response.content)
        }
    }
}