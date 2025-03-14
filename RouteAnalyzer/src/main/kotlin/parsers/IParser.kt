package com.routeranalyzer.parsers

import java.io.InputStreamReader

interface IParser<T> {
    fun parse(inputStream: InputStreamReader): T
}