
package persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import models.Note
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import java.time.LocalDateTime

class YAMLSerializer(private val file: File) : Serializer {

    private val mapper = ObjectMapper(YAMLFactory()).apply {

        registerModule(
            SimpleModule().addSerializer(
                LocalDateTime::class.java, LocalDateTimeSerializer()
            )
        )
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }

    @Throws(Exception::class)
    override fun read(): Any {
        val objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.findAndRegisterModules()
        val inputStream = FileReader(file)
        val obj = objectMapper.readValue(inputStream, Note::class.java)
        inputStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.findAndRegisterModules()
        val outputStream = FileWriter(file)
        objectMapper.writeValue(outputStream, obj)
        outputStream.close()
    }

    private class LocalDateTimeSerializer : com.fasterxml.jackson.databind.JsonSerializer<LocalDateTime>() {
        override fun serialize(value: LocalDateTime?, gen: com.fasterxml.jackson.core.JsonGenerator?, serializers: com.fasterxml.jackson.databind.SerializerProvider?) {
            if (value != null && gen != null) {
                gen.writeString(value.toString())
            }
        }
    }
}
