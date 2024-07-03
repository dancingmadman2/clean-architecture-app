package com.example.movieTracker.di.watchlist_datastore


import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object WatchlistSerializer : Serializer<Set<Int>> {
    override val defaultValue: Set<Int> = emptySet()

    override suspend fun readFrom(input: InputStream): Set<Int> {
        return try {
            val byteArray = input.readBytes()
            val stringSet =
                byteArray.decodeToString().split(",").mapNotNull { it.toIntOrNull() }.toSet()
            stringSet
        } catch (exception: InvalidProtocolBufferException) {
            emptySet()
        }
    }

    override suspend fun writeTo(t: Set<Int>, output: OutputStream) {
        val byteArray = t.joinToString(",").encodeToByteArray()
        output.write(byteArray)
    }
}