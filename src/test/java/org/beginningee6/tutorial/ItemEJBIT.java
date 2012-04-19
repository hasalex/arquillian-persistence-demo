package org.beginningee6.tutorial;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Alexis Hassler - http://www.alexis-hassler.com
 */
//@RunWith(Arquillian.class)
public class ItemEJBIT {

    @Mock EntityManager em;
    @Mock Query namedQuery;
    @Mock IsbnGenerator isbnGenerator;
    @Mock Logger logger;
    @InjectMocks ItemEJB itemEJB;

//    @Deployment
//    public static JavaArchive deploy() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addPackage(ItemEJB.class.getPackage())
//                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
    /* */
//    @EJB DBInit dbInit;
//    @Before
//    public void initDB() {
//        dbInit.initDatabase();
//    }

    @Test
    public void shouldCreateCDReturnCreatedCD() throws Exception {
        final CD cd = new CD();
        cd.setTitle("title");

        CD cdFound = itemEJB.createCD(cd);
        assertSame(cd, cdFound);
    }

    @Test
    public void shouldCreateBookReturnCreatedBookWithIsbn() throws Exception {
        final Book book = new Book();
        book.setTitle("title");
        
        Book bookFound = itemEJB.createBook(book);
        assertSame(book, bookFound);
        assertNotNull(book.getIsbn());
        assertNotNull(book.getId());
    }

    @Test
    public void shouldGetBookSimplyReturnFoundBook() throws Exception {
        Book bookFound = itemEJB.getBook(1L);
        assertNotNull("Book found is null", bookFound);
        assertEquals("Book found has wrong id", Long.valueOf(1L), bookFound.getId());
    }

    @Test
    public void shouldFindAllBooksSimplyReturnFoundBooks() throws Exception {
        final List<Book> allBooks = itemEJB.findAllBooks();
        assertEquals("List size has been modified", DBInit.NUM_BOOKS, allBooks.size());
    }

    @Test
    public void shouldFindAllScifiBooksSimplyReturnFoundBooks() throws Exception {
        final List<Book> scifiBooks = itemEJB.findAllScifiBooks();
        assertEquals("List size has been modified", 0, scifiBooks.size());
    }

    @Test
    public void shouldFindAllCDsSimplyReturnFoundCDs() throws Exception {
        final List<CD> allCDs = itemEJB.findAllCDs();
        assertEquals("List size has been modified", DBInit.NUM_CDS, allCDs.size());
    }
}
