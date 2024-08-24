package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    //@RequiredArgsConstructor 롬복 문법 붙여줘야 동작함.
    private final NoticeRepository noticeRepository;


    @GetMapping("/notice")
    String list(Model model){
        List<Notice> result = noticeRepository.findAll();
        model.addAttribute("notice", result);

        var b=new Notice();
        System.out.println(b.toString());
        
        return "notice.html";
    }
}
