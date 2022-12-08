package com.ngedev.newsapplicationcompose.utils

import androidx.room.withTransaction
import com.ngedev.newsapplicationcompose.data.source.local.LocalDatabase

class TransactionProvider(private val db: LocalDatabase) {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return db.withTransaction(block)
    }
}