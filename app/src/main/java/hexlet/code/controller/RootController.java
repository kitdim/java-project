package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Collections;

public final class RootController {
    public static void index(Context ctx) {
        BasePage page = new BasePage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("index.jte", Collections.singletonMap("page", page)).status(HttpStatus.OK);
    }

}
