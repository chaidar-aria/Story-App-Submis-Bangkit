import com.chaidar.storyappsubmis.backend.response.AllStoryResponse
import com.chaidar.storyappsubmis.backend.response.LoginResponse
import com.chaidar.storyappsubmis.backend.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    @Headers("No-Authentication: true")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    @Headers("No-Authentication: true")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int = 0
    ): Call<AllStoryResponse>


//    @GET("stories")
//    fun getStories(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int? = null,
//        @Query("size") size: Int? = null,
//        @Query("location") location: Int = 0
//    ): Call<AllStoryResponse>
}
