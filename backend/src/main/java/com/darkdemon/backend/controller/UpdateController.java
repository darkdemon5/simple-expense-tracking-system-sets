package com.darkdemon.backend.controller;


import com.darkdemon.backend.dto.UpdateEmailDTO;
import com.darkdemon.backend.dto.UpdatePasswordDTO;
import com.darkdemon.backend.dto.UserUpdateDTO;
import com.darkdemon.backend.service.UpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Update")
public class UpdateController {

    private final UpdateService updateService;

    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PutMapping("/User/{id}")
    private ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return updateService.updateUser(id, userUpdateDTO);
    }

    @PutMapping("/Email/{id}")
    private ResponseEntity<?> updateEmail(@PathVariable Long id, @RequestBody UpdateEmailDTO updateEmailDTO) {
        return updateService.updateEmail(id, updateEmailDTO);
    }

    @PutMapping("/Password/{id}")
    private ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        return updateService.updatePassword(id, updatePasswordDTO);
    }

}
