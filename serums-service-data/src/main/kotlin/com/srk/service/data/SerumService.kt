package com.srk.service.data

import com.srk.shared.Serum

interface SerumService {
    fun create(serum: Serum): Boolean
    fun delete(id: Int): Boolean
    fun read(): List<Serum>
    fun read(id: Int): Serum?
    fun update(id: Int, serum: Serum): Boolean
}
