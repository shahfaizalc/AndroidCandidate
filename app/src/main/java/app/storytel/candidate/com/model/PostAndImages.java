package app.storytel.candidate.com.model;

import java.util.List;

/**
 * PostAndImages model class
 */
public class PostAndImages {

    /**
     * List of Post object
     */

    public List<Post> mPosts;
    /**
     * List of Photos object
     */
    public List<Photo> mPhotos;

    public PostAndImages(List<Post> post, List<Photo> photos) {
        mPosts = post;
        mPhotos = photos;
    }
}
