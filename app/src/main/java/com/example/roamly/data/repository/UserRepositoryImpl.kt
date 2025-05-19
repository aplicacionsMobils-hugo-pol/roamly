package com.example.roamly.data.repository

import com.example.roamly.data.local.dao.UserDao
import com.example.roamly.data.local.mapper.toDomain
import com.example.roamly.data.local.mapper.toEntity
import com.example.roamly.domain.models.User
import com.example.roamly.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserById(id: String): User? {
        return userDao.getUserById(id)?.toDomain()
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers().map { it.toDomain() }
    }

    override suspend fun updateUser(user: User): Boolean {
        return userDao.updateUser(user.toEntity()) > 0
    }

    override suspend fun deleteUser(id: Int): Boolean {
        return userDao.deleteUser(id) > 0
    }

    override suspend fun insertUser(user: User): Boolean {
        return userDao.addUser(user.toEntity()) > 0
    }

    override suspend fun getUserByFirebaseUid(firebaseUid: String): User? {
        return userDao.getUserByFirebaseUid(firebaseUid)?.toDomain()
    }

}
