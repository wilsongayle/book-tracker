package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Quote;
import com.github.wilsongayle.book_tracker.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
