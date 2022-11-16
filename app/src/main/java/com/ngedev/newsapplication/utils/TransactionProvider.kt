package com.ngedev.newsapplication.utils

import androidx.room.withTransaction
import com.ngedev.newsapplication.data.source.local.LocalDatabase

class TransactionProvider(private val db: LocalDatabase) {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return db.withTransaction(block)
    }
}