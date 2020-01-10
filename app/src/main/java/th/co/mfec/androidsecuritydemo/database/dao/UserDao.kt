package th.co.mfec.androidsecuritydemo.database.dao

import androidx.room.*
import th.co.mfec.androidsecuritydemo.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM userEntity")
    suspend fun inquiryUser(): UserEntity

    @Query("UPDATE userEntity SET displayName = :name ,userEmail = :email ,userImageUrl = :url ")
    suspend fun updateUser(name: String, email: String, url: String)

    @Query("DELETE FROM userEntity")
    suspend fun deleteUser()
}