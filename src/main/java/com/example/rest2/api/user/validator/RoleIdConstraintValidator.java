package com.example.rest2.api.user.validator;
import com.example.rest2.api.user.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class RoleIdConstraintValidator implements ConstraintValidator< RoleIdConstraint, List<Integer>> {
    private final UserMapper userMapper;
    @Override
    public boolean isValid(List<Integer> roleIds, ConstraintValidatorContext context) {
        for(Integer roleId: roleIds){
            if(!userMapper.checkRole(roleId)){
                return false;
            }
        }
        return true;

    }
}
