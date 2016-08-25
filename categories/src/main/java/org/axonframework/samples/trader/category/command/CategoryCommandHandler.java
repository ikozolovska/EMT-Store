package org.axonframework.samples.trader.category.command;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.axonframework.samples.trader.api.category.CreateCategoryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * Created by DELL-PC on 6/13/2016.
 */
public class CategoryCommandHandler {

    private Repository<Category> repository;

    @CommandHandler
    public void handleCreateCategory(CreateCategoryCommand command) {
        Category category = new Category(command.getCategoryId(),
                command.getCategoryName(),
                command.isMainCategory(),
                command.getParentCategory());
        repository.add(category);
    }

    @Autowired
    @Qualifier("categoryRepository")
    public void setRepository(Repository<Category> categoryRepository) {
        this.repository = categoryRepository;
    }
}