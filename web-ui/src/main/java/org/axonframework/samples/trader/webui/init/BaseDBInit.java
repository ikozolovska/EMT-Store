package org.axonframework.samples.trader.webui.init;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.samples.trader.api.users.CreateUserCommand;
import org.axonframework.samples.trader.api.users.UserId;

/**
 * Base class for all DBInit implementations
 */
public abstract class BaseDBInit implements DBInit {
    private CommandBus commandBus;

    protected BaseDBInit(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Override
    public void createItems() {
        initializeDB();

        UserId buyer1 = createuser("Dragan", "Andonovski", "buyer1", "draganandonovski@gmail.com", "070-333-444", "buyer1");
        UserId buyer2 = createuser("Martin", "Andonovski", "buyer2", "martinandonovski@gmail.com", "070-333-444", "buyer2");
        UserId buyer3 = createuser("Ivan", "Krstevski", "buyer3", "ivankrstevski@gmail.com", "070-333-444", "buyer3");
        UserId admin = createuser("Administrator", "Administrator", "admin", "admin@admin.admin", "000-000-000", "admin");

        additionalDBSteps();
    }

    UserId createuser(String firstName, String lastName, String userName, String email, String phone, String password) {
        UserId userId = new UserId();
        CreateUserCommand createUser = new CreateUserCommand(userId, firstName, lastName, userName, email, phone, password);
        commandBus.dispatch(new GenericCommandMessage<>(createUser));
        return userId;
    }

    abstract void initializeDB();

    abstract void additionalDBSteps();
}