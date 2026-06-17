package web.repository;

import web.model.Role;

import java.util.List;

public interface RoleRepo {
    List<Role> allRoles();
    Role getDefaultRole();
    Role getRoleByName(String name);
    Role getRoleById(Long id);




}
