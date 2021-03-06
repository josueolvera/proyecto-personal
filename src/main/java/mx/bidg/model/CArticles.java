/**
 *
 * @author rafael
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@DynamicUpdate
@Table(name = "C_ARTICLES")

public class CArticles implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ARTICLE")
    @JsonView(JsonViews.Root.class)
    private Integer idArticle;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "ARTICLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String articleName;

    @Column(name = "ID_PRODUCT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idProduct;

    @Column(name = "ID_ARTICLE_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idArticleCategory;

    @Column(name = "HAS_SERIAL_NUMBER")
    @JsonView(JsonViews.Root.class)
    private Boolean hasSerialNumber;

    @OneToOne
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID_PRODUCT")
    @JsonView(JsonViews.Embedded.class)
    private CProducts product;

    @ManyToOne
    @JoinColumn(name = "ID_ARTICLE_CATEGORY", referencedColumnName = "ID_ARTICLE_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private CArticlesCategories articlesCategories;

    public CArticles() {
    }

    public CArticles(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public CArticles(Integer idArticle, String articleName) {
        this.idArticle = idArticle;
        this.articleName = articleName;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public CProducts getProduct() {
        return product;
    }

    public void setProduct(CProducts product) {
        this.product = product;
    }

    public Integer getIdArticleCategory() {
        return idArticleCategory;
    }

    public void setIdArticleCategory(Integer idArticleCategory) {
        this.idArticleCategory = idArticleCategory;
    }

    public CArticlesCategories getArticlesCategories() {
        return articlesCategories;
    }

    public void setArticlesCategories(CArticlesCategories articlesCategories) {
        this.articlesCategories = articlesCategories;
    }

    public Boolean hasSerialNumber() {
        return hasSerialNumber;
    }

    public void hasSerialNumber(Boolean hasSerialNumber) {
        this.hasSerialNumber = hasSerialNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticle != null ? idArticle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CArticles)) {
            return false;
        }
        CArticles other = (CArticles) object;
        if ((this.idArticle == null && other.idArticle != null) || (this.idArticle != null && !this.idArticle.equals(other.idArticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CArticles[ idArticle=" + idArticle + " ]";
    }
}
