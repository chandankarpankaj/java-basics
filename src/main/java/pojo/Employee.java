package pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString()
public class Employee {

    public static final Employee DEF_EMP = new Employee(0, "No Name");

    @Getter
    @Setter
    int id;

    @Getter
    @Setter
    String name;

    public Employee(@NonNull int id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

}
