/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package at.yawk.javap

import at.yawk.javap.model.Paste
import at.yawk.javap.model.ProcessingInput
import javax.inject.Inject

/**
 * @author yawkat
 */
class DefaultPaste @Inject constructor(sdkProvider: SdkProvider, processor: Processor) {
    val defaultPastes = sdkProvider.defaultSdkByLanguage.map {
        val code = when(it.key) {
            SdkLanguage.JAVA -> """import java.util.*;
import lombok.*;

public class Main {
    public Main() {
        int i = 0;
        i++;
    }
}"""
            SdkLanguage.KOTLIN -> """import java.util.*
import kotlinx.coroutines.*

class Main() {
    init {
        var i = 0
        i++
    }
}"""
            SdkLanguage.SCALA -> """object Main {
    def test(i: Int) = i + 1
}"""
        }
        val input = ProcessingInput(code, it.value.name)
        Paste("default:${it.key}", "", input, processor.process(input))
    }
}