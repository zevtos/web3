package ru.zevtos.webserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import ru.zevtos.webserver.validation.ValidR;
import ru.zevtos.webserver.validation.ValidX;

import java.beans.JavaBean;
import java.io.Serializable;

/**
 * Result - класс, представляющий результат проверки попадания точки в область.
 * <p>
 * Содержит данные о координатах точки (x, y), радиусе (r), идентификаторе результата,
 * а также информации о попадании точки в область.
 */
@Entity
@Table(name = "results")
@JavaBean
public class Result implements Serializable {

    /**
     * Уникальный идентификатор результата (если сохраняется в базу данных).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Координата X точки, проверяемая на попадание в область.
     * Значение должно удовлетворять валидации, заданной аннотацией @ValidX.
     */
    @ValidX
    private double x;

    /**
     * Координата Y точки, проверяемая на попадание в область.
     * Допустимое значение должно находиться в диапазоне от -3 до 5.
     */
    @Min(-3)
    @Max(5)
    private double y;

    /**
     * Радиус R, определяющий размер области проверки.
     * Значение должно удовлетворять валидации, заданной аннотацией @ValidR.
     */
    @ValidR
    private double r;

    /**
     * Флаг, показывающий, попала ли точка в заданную область.
     */
    private boolean hit;

    // Конструкторы

    /**
     * Конструктор без аргументов, необходимый для JPA.
     */
    public Result() {
    }

    /**
     * Конструктор со всеми аргументами для создания объекта с заданными значениями полей.
     */
    public Result(Long id, double x, double y, double r, boolean hit) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    // Переопределение метода toString

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", hit=" + hit +
                '}';
    }

    // Переопределение методов equals и hashCode

    /**
     * Сравнение объектов на основе идентификатора id, что является стандартной практикой для JPA-сущностей.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id != null && id.equals(result.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // Реализация паттерна Builder

    /**
     * Статический метод для получения объекта Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Внутренний статический класс Builder для пошагового создания объекта Result.
     */
    public static class Builder {
        private Long id;
        private double x;
        private double y;
        private double r;
        private boolean hit;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder x(double x) {
            this.x = x;
            return this;
        }

        public Builder y(double y) {
            this.y = y;
            return this;
        }

        public Builder r(double r) {
            this.r = r;
            return this;
        }

        public Builder hit(boolean hit) {
            this.hit = hit;
            return this;
        }

        /**
         * Создает и возвращает объект Result с заданными значениями.
         */
        public Result build() {
            return new Result(id, x, y, r, hit);
        }
    }
}