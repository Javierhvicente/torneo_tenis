package tenistas.repositories

import database.DatabaseConnection
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import tenistas.models.Tenista
import java.sql.Connection
import java.time.LocalDate
import java.time.LocalDateTime

class TenistasRepositoryImplTest {

    private val connection = DatabaseConnection()
    private lateinit var repository: TenistasRepositoryImpl
    @BeforeEach
    fun connect(){
        connection.connect()
        repository = connection.connect()?.let { TenistasRepositoryImpl(it) }!!
    }

    @Test
    fun getTenistaByName() {
        //arrange
        val Nadal = Tenista(1,"Rafael Nadal", "Argentina", 185, 75, 2650, "Derecha", LocalDate.of(1985, 10, 25), LocalDateTime.now(), LocalDateTime.now())
        repository.saveTenista(Nadal)
        //act
        val tenista = repository.getTenistaByName("Rafael Nadal")
        //assert
        assertNotNull(tenista)
        assertEquals(Nadal, tenista)
    }

    @Test
    fun getTenistaByNameNotFound() {
        //arrange
        val Nadal = Tenista(1,"Rafael Nadal", "Argentina", 185, 75, 2650, "Derecha", LocalDate.of(1985, 10, 25), LocalDateTime.now(), LocalDateTime.now())
        repository.saveTenista(Nadal)
        //act
        val tenista = repository.getTenistaByName("Rafael Nad")
        //assert
        assertNull(tenista)
        assertNotEquals(Nadal, tenista)
    }
}