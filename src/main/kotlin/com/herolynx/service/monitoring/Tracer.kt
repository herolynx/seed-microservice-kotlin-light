package com.herolynx.service.monitoring

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private fun <T> logger(t: T): Logger {
    val o = t as Any
    return LoggerFactory.getLogger(o.javaClass)
}

fun <T> T.debug(msg: String) {
    logger(this).debug(msg)
}

fun <T> T.debug(msg: String, t: Throwable) {
    logger(this).debug(msg, t)
}

fun <T> T.info(msg: String) {
    logger(this).info(msg)
}

fun <T, E : Throwable> T.error(msg: String, t: E?) {
    logger(this).error(msg, t)
}

fun <T> T.error(msg: String) {
    logger(this).error(msg)
}

fun <T, E : Throwable> T.warn(msg: String, t: E?) {
    logger(this).warn(msg, t)
}


fun <T, E : Throwable> T.warn(msg: String) {
    logger(this).warn(msg)
}