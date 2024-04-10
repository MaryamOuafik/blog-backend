package bloggy.models;

import java.sql.Date;
import java.util.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPost;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;
    
    @Column(nullable = true)
    private Date creation_date;

    @Column(nullable = true)
    private Date publishing_date;
    
    @ManyToOne
    @JoinColumn(name = "publisher")
    private User publisher;
    
    @ManyToOne
    @JoinColumn(name = "reviewer")
    private User reviewer;
    
  /*  @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "posts_categories",
        joinColumns = @JoinColumn(name = "id_post"),
        inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private Set<Category> categories = new HashSet<>();*/





}

