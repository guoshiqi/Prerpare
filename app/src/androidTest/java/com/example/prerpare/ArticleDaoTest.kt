package com.example.prerpare

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.prerpare.data.db.AppDatabase
import com.example.prerpare.data.db.dao.ArticleDAO
import com.example.prerpare.data.model.Article
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class ArticleDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var articleDao: ArticleDAO

    @Before
    fun setUp() {
        // 创建内存数据库，不会写入磁盘
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()  // 测试可以允许主线程
        articleDao = database.articleDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetArticle() = runBlocking {
        val article = Article(title = "Test", desc = "Content")
        val id = articleDao.insert(article)

        val loaded = articleDao.getArticleById(id)
        Assert.assertNotNull(loaded)
        Assert.assertEquals("Test", loaded?.title)
        Assert.assertEquals("Content", loaded?.desc)
    }

    @Test
    fun updateArticle() = runBlocking {
        val article = Article(title = "Old", desc = "Old content")
        val id = articleDao.insert(article)

        val updated = Article(id = id, title = "New", desc = "New content")
        articleDao.update(updated)

        val loaded = articleDao.getArticleById(id)
        Assert.assertEquals("New", loaded?.title)
        Assert.assertEquals("New content", loaded?.desc)
    }

    @Test
    fun deleteArticle() = runBlocking {
        val article = Article(title = "ToDelete", desc = "Content")
        val id = articleDao.insert(article)

        val loaded = articleDao.getArticleById(id)
        Assert.assertNotNull(loaded)

        articleDao.delete(loaded!!)
        val afterDelete = articleDao.getArticleById(id)
        Assert.assertNull(afterDelete)
    }

    @Test
    fun searchByTitle() = runBlocking {
        val a1 = Article(title = "Hello World", desc = "C1")
        val a2 = Article(title = "Hello Kotlin", desc = "C2")
        val a3 = Article(title = "Random", desc = "C3")
        articleDao.insert(a1)
        articleDao.insert(a2)
        articleDao.insert(a3)

        val results = articleDao.findArticlesByTitle("Hello")
        Assert.assertEquals(2, results.size)
    }
}