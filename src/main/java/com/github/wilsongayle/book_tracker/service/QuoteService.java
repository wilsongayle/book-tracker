package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.Quote;
import com.github.wilsongayle.book_tracker.exception.InvalidRequestException;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.QuoteRepository;
import com.github.wilsongayle.book_tracker.util.RepositoryLookup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final BookRepository bookRepository;

    public QuoteService(QuoteRepository quoteRepository, BookRepository bookRepository) {
        this.quoteRepository = quoteRepository;
        this.bookRepository = bookRepository;
    }

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Quote createQuote(Quote quote) {
        Book book = quote.getBook();
        if (book == null) {
            throw new InvalidRequestException("A book is required to create a quote");
        }
        Book fullBook = RepositoryLookup.resolveOrThrow(bookRepository, book.getId());
        quote.setBook(fullBook);
        return quoteRepository.save(quote);
    }

    public Optional<Quote> getQuoteById(UUID id) {
        return quoteRepository.findById(id);
    }

    public boolean deleteQuoteById(UUID id) {
        if (!quoteRepository.existsById(id)) {
            return false;
        }
        quoteRepository.deleteById(id);
        return true;
    }
}
