package dev.lstr.llevateclaro.util

import android.support.test.InstrumentationRegistry
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by lesternr on 7/1/18.
 */
object ResourceLoader {
    fun Any.loadContentFromFile(fileName: String?): String {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.resources.assets.open(fileName)
        BufferedReader(InputStreamReader(inputStream)).use { br ->
            val sb = StringBuilder()
            var line = br.readLine()

            while (line != null) {
                sb.append(line)
                sb.append("\n")
                line = br.readLine()
            }
            return sb.toString()
        }
    }
}