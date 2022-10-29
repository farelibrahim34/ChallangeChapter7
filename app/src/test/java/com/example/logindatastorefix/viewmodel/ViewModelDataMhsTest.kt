package com.example.logindatastorefix.viewmodel

import com.example.logindatastorefix.model.ResponseDataMhsItem
import com.example.logindatastorefix.network.APIInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class ViewModelDataMhsTest{
    lateinit var servis : APIInterface
    @Before
    fun setUp(){
        servis = mockk()
    }

    @Test
    fun getDataMhsTest(): Unit = runBlocking {
//        mocking GIVEN
        val respAllDataMhs = mockk <Call<List<ResponseDataMhsItem>>>()

        every {
            runBlocking {
                servis.getAllDataMhs()
            }
        } returns respAllDataMhs

//        System Under Test (WHEN)
        val result = servis.getAllDataMhs()

        verify {
            runBlocking { servis.getAllDataMhs() }
        }
        assertEquals(result, respAllDataMhs)

    }
    @Test
    fun testGetDataMhs(){
        val respAllDataMhs = mockk <Call<List<ResponseDataMhsItem>>>()
        every {
            servis.getAllDataMhs()

        }returns respAllDataMhs

        //        System Under Test (WHEN)
        val result = servis.getAllDataMhs()

        verify {
            servis.getAllDataMhs()
        }
        Assert.assertEquals(result, respAllDataMhs)
    }
}