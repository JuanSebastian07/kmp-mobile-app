package org.composemultiplatform.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.expenseApp.database.AppDatabase

class IOSDatabaseDriverFactory(): DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            AppDatabase.Schema,
            "AppDatabase.db"
        )
    }

}