package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Quote;
import com.github.wilsongayle.book_tracker.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Quote createQuote(Quote quote) {
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
