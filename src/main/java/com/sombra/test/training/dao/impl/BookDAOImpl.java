package com.sombra.test.training.dao.impl;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.sombra.test.training.dao.interfaces.BookDAO;
import com.sombra.test.training.entities.Author;
import com.sombra.test.training.entities.Book;
import com.sombra.test.training.entities.Genre;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private ProjectionList bookProjection;

    public BookDAOImpl() {
        bookProjection = Projections.projectionList();
        bookProjection.add(Projections.property("id"), "id");
        bookProjection.add(Projections.property("name"), "name");
        bookProjection.add(Projections.property("image"), "image");
        bookProjection.add(Projections.property("genre"), "genre");
        bookProjection.add(Projections.property("pageCount"), "pageCount");
        bookProjection.add(Projections.property("isbn"), "isbn");
        bookProjection.add(Projections.property("publisher"), "publisher");
        bookProjection.add(Projections.property("author"), "author");
        bookProjection.add(Projections.property("publishYear"), "publishYear");
        bookProjection.add(Projections.property("descr"), "descr");
        bookProjection.add(Projections.property("rating"), "rating");
        bookProjection.add(Projections.property("voteCount"), "voteCount");
    }


    @Transactional
    @Override
    public List<Book> getBooks() {
        List<Book> books = createBookList(createBookCriteria());
        return books;
    }

    @Transactional
    @Override
    public List<Book> getBooks(Author author) {
        List<Book> books = createBookList(createBookCriteria().add(Restrictions.ilike("author.fio", author.getFio(), MatchMode.ANYWHERE)));
        return books;
    }

    @Transactional
    @Override
    public List<Book> getBooks(String bookName) {
        List<Book> books = createBookList(createBookCriteria().add(Restrictions.ilike("b.name", bookName, MatchMode.ANYWHERE)));
        return books;
    }

    @Transactional
    @Override
    public List<Book> getBooks(Genre genre) {
        List<Book> books = createBookList(createBookCriteria().add(Restrictions.ilike("genre.name", genre.getName(), MatchMode.ANYWHERE)));
        return books;
    }

    @Transactional
    @Override
    public List<Book> getBooks(Character letter) {
        List<Book> books = createBookList(createBookCriteria().add(Restrictions.ilike("b.name", letter.toString(), MatchMode.START)));
        return books;
    }

    @Transactional
    @Override
    public Object getFieldValue(Long id, String fieldName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Book.class);
        criteria.setProjection(Property.forName(fieldName));
        criteria.add(Restrictions.eq("id", id));
        return criteria.uniqueResult();
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from Book where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private List<Book> createBookList(DetachedCriteria bookListCriteria) {
        Criteria criteria = bookListCriteria.getExecutableCriteria(sessionFactory.getCurrentSession());
        criteria.addOrder(Order.asc("b.name")).setProjection(bookProjection).setResultTransformer(Transformers.aliasToBean(Book.class));
        return criteria.list();
    }

    private void createAliases(DetachedCriteria criteria) {
        criteria.createAlias("b.author", "author");
        criteria.createAlias("b.genre", "genre");
        criteria.createAlias("b.publisher", "publisher");
    }

    private DetachedCriteria createBookCriteria() {
        DetachedCriteria booklistCriteria = DetachedCriteria.forClass(Book.class, "b");
        createAliases(booklistCriteria);
        return booklistCriteria;
    }

}
