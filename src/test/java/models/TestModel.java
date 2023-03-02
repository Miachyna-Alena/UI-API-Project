package models;

import lombok.Data;

import java.util.Objects;

@Data
public class TestModel {

    private String name;
    private String method;
    private String status;
    private String startTime;
    private String endTime;
    private String duration;


    @Override
    public String toString() {
        return String.format("Test: %n" +
                        "Test name: %s%n" +
                        "Test method: %s%n" +
                        "Latest test result: %s%n" +
                        "Latest test start time: %s%n" +
                        "Latest test end time: %s%n" +
                        "Latest test duration: %s%n",
                name, method, status, startTime, endTime, duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestModel user = (TestModel) o;
        return Objects.equals(name, user.name) && Objects.equals(method, user.method)
                && Objects.equals(status, user.status) && Objects.equals(startTime, user.startTime)
                && Objects.equals(endTime, user.endTime) && Objects.equals(duration, user.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, method, status, startTime, endTime, duration);
    }
}
