package com.mayokunadeniyi.instantweather.kaspresso

class TestCaseDataProducer {

    /**
     * Init data f.e. with a server and transform it to testcase abstractions
     */
    fun initData(action: (TestCaseDsl.() -> Unit)?): TestCaseDsl {
        val testCaseDsl = TestCaseDsl().also { testCaseDsl -> action?.let { testCaseDsl.apply(it) } }
        // Init data at server side or by another way
        return testCaseDsl
    }
}