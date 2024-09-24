package database

import config.Config
import org.lighthousegames.logging.logging
import tenistas.mapper.logger
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
private val looger = logging()
class DatabaseConnection {

    private val connection: Connection
        get() = DriverManager.getConnection(Config.dataBaseUrl)

    fun initializeDatabase() {
        useConnection { conn ->
            val dropTableSql = "DROP TABLE IF EXISTS tenistas;"
            val createTableSql = """
                CREATE TABLE IF NOT EXISTS tenistas (
                    id TEXT PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    pais TEXT NOT NULL,
                    altura INTEGER NOT NULL,
                    peso INTEGER NOT NULL,
                    puntos INTEGER NOT NULL,
                    mano TEXT NOT NULL,
                    fecha_nacimiento TEXT NOT NULL,
                    created_at TEXT NOT NULL,
                    updated_at TEXT NOT NULL
                );
            """.trimIndent()

            conn.createStatement().use { statement ->
                statement.execute(dropTableSql)

                statement.execute(createTableSql)
            }
        }
    }


    fun <T> useConnection(action: (Connection) -> T): T? {
        var conn: Connection? = null
        return try {
            conn = connection
            action(conn)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        } finally {
            conn?.close()
        }
    }
}