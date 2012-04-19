package org.beginningee6.tutorial;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Alexis Hassler - http://www.alexis-hassler.com
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE)
@DataSource("jdbc/sample")
public class ItemEJBDataSetIT {

    @Deployment
    public static JavaArchive deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                         .addPackage(ItemEJB.class.getPackage())
                         .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                         .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB ItemEJB itemEJB;

    @Test
    @UsingDataSet("book1.yml")
    public void shouldGetBookSimplyReturnFoundBook() throws Exception {
        Book bookFound = itemEJB.getBook(1L);
        assertNotNull("Book found is null", bookFound);
        assertEquals("Book found has wrong id", Long.valueOf(1L), bookFound.getId());
    }

    @Test
    @Transactional(TransactionMode.DISABLED)
    @UsingDataSet("books.yml")
    @ShouldMatchDataSet(value={"books.yml", "expected-book.yml"}, excludeColumns = {"id"})
    public void shouldCreateBookReturnCreatedBookWithIsbn() throws Exception {
        final Book book = new Book();
        book.setTitle("Book Title");
        
        Book bookFound = itemEJB.createBook(book);
        assertSame(book, bookFound);
        assertNotNull("ISBN is null", book.getIsbn());
        assertNotNull("ID has not been generated", book.getId());
    }

    @Test
    @UsingDataSet("books.yml")
    public void shouldFindAllBooksSimplyReturnFoundBooks() throws Exception {
        final List<Book> allBooks = itemEJB.findAllBooks();
        assertEquals("List size has been modified", DBInit.NUM_BOOKS, allBooks.size());
    }

    @Test
    public void shouldCreateCDReturnCreatedCD() throws Exception {
        final CD cd = new CD();
        cd.setTitle("CD Title");
        
        CD cdFound = itemEJB.createCD(cd);
        assertSame(cd, cdFound);
    }

    @Test
    @UsingDataSet("cds.yml")
    public void shouldFindAllCDsSimplyReturnFoundCDs() throws Exception {
        final List<CD> allCDs = itemEJB.findAllCDs();
        assertEquals("List size has been modified", DBInit.NUM_CDS, allCDs.size());
    }
}
