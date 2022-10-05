package com.jinseok.projectboard.repository;

import com.jinseok.projectboard.config.JpaConfig;
import com.jinseok.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

//@ActiveProfiles("testdb")
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_WhenSelecting_ThenWorks_Test() {
        // given - data


        //when - selecting
        List<Article> articles = articleRepository.findAll();

        //then - worksFine
        assertThat(articles)
                .isNotNull()
                .hasSize(5);
    }

    @DisplayName("inssert 테스트")
    @Test
    void givenTestData_WhenInsert_ThenWorks_Test() {
        // given - data
        Long previousCount = articleRepository.count();

        //when
        Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));

        //then - worksFine
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_WhenUpdating_ThenWorks_Test() {
        // given - data
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedhashtag = "#springboot";
        article.setHashtag(updatedhashtag);

        //when
        Article savedArticle = articleRepository.saveAndFlush(article);

        //then - worksFine
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedhashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_WhenDeleting_ThenWorks_Test() {
        // given - data
        Article article = articleRepository.findById(1L).orElseThrow();
        Long previousArticleCount = articleRepository.count();
        Long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        //when
        articleRepository.delete(article);

        //then - worksFine
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount -1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount -deletedCommentsSize);
    }
}