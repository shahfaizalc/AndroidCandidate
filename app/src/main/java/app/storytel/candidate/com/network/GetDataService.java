package app.storytel.candidate.com.network;

import java.util.List;

import app.storytel.candidate.com.model.Comment;
import app.storytel.candidate.com.model.Photo;
import app.storytel.candidate.com.model.Post;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Class to Get data service
 */
public interface GetDataService {

    @GET("/photos")
    Observable<List<Photo>> getAllPhotos();

    @GET("/posts")
    Observable<List<Post>> getAllPosts();

    @GET("/posts/{id}/comments")
    Observable<List<Comment>> getAllComments(@Path("id") String id);
}