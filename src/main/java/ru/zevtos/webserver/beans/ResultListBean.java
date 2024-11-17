package ru.zevtos.webserver.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.zevtos.webserver.entities.Result;
import ru.zevtos.webserver.services.ResultService;

import java.io.Serializable;
import java.util.List;

@Named("resultListBean")
@SessionScoped
public class ResultListBean implements Serializable {

    @Inject
    private ResultService resultService;

    /**
     * Добавляет новый результат и сохраняет его в базе данных.
     *
     * @param result объект Result, который нужно добавить.
     */
    public void addResult(Result result) {
        // Сохраняем результат в базе данных
        resultService.save(result);
    }

    /**
     * Очищает все результаты из базы данных.
     */
    public void clearResults() {
        // Удаляем все результаты из базы данных
        resultService.deleteAll();
    }

    /**
     * Удаляет конкретный результат по его ID.
     *
     * @param id идентификатор результата, который нужно удалить.
     */
    public void deleteResult(Long id) {
        // Удаляем результат из базы данных
        resultService.deleteById(id);
    }

    /**
     * Возвращает список результатов из базы данных для отображения.
     *
     * @return список объектов Result.
     */
    public List<Result> getResults() {
        // Загружаем актуальный список результатов из базы данных
        return resultService.findAll();
    }
}
