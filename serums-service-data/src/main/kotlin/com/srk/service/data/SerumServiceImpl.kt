package com.srk.service.data

import com.srk.shared.Phase
import com.srk.shared.Serum
import java.time.LocalDate

class SerumServiceImpl : SerumService {
    override fun create(serum: Serum): Boolean {
        val newSerum =
            Serum(serums.size, serum.name, serum.phase, serum.tester, serum.potency, serum.testDate)
        serums = serums + newSerum
        return true
    }

    override fun delete(id: Int): Boolean {
        val serum = serums.getOrElse(id) {
            return false
        }

        serums = serums.filter { it.id != serum.id }
        return true
    }

    override fun read(): List<Serum> {
        return serums
    }

    override fun read(id: Int): Serum? {
        return serums.getOrNull(id)
    }

    override fun update(id: Int, serum: Serum): Boolean {
        serums = serums.filter { it.id != serum.id }
        serums += serum
        return true
    }
}

val serum1 = Serum(
    0,
    "Serum Worms Sackrider",
    Phase.ALPHA,
    "Felice Mienn",
    0.78,
    LocalDate.of(2022, 1, 20)
)

val serum2 = Serum(
    1,
    "Serum Squids Overturf",
    Phase.BETA,
    "Praeda Rakespear",
    0.87,
    LocalDate.of(2022, 1, 20)
)

var serums = listOf(serum1, serum2)
