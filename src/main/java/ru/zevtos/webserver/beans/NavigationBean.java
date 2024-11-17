package ru.zevtos.webserver.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("navigationBean")
@RequestScoped
public class NavigationBean {

    /**
     * Переход на основную страницу.
     *
     * @return навигационный исход для перехода на main.xhtml.
     */
    public String toMainPage() {
        return "main?faces-redirect=true";
    }

    /**
     * Переход на стартовую страницу.
     *
     * @return навигационный исход для перехода на index.xhtml.
     */
    public String toIndexPage() {
        return "index?faces-redirect=true";
    }
}
