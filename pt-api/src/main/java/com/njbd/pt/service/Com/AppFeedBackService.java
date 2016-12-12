package com.njbd.pt.service.Com;

import com.njbd.pt.model.AppFeeback;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AppFeedBackService {
    Map addAppFeedBack(AppFeeback appFeeback);
}
