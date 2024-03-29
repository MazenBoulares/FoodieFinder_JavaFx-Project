package com.example.javefx_project_v3.Services;
import com.example.javefx_project_v3.Entitys.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IService<P>{
    void ajouter(P p) throws SQLException;
    void update(P p) throws SQLException;
    void delete(int id) throws  SQLException;

    ArrayList<P> consulterAll() throws SQLException;

    User get(int id) throws SQLException;
}
