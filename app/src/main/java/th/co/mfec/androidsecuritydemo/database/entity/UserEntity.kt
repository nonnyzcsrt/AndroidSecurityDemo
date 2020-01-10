package th.co.mfec.androidsecuritydemo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userEntity")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "userId")
    var userId: String? = "",

    @ColumnInfo(name = "userLogin")
    var userLogin: String? = "",

    @ColumnInfo(name = "userEmail")
    var userEmail: String? = "",

    @ColumnInfo(name = "displayName")
    var displayName: String? = "",

    @ColumnInfo(name = "userImageUrl")
    var userImageUrl: String? = ""
)