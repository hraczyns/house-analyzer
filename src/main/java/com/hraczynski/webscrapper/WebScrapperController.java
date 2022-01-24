package com.hraczynski.webscrapper;

import com.hraczynski.webscrapper.dto.GroupsOffers;
import com.hraczynski.webscrapper.dto.SingleOffer;
import com.hraczynski.webscrapper.dto.Statistics;
import com.hraczynski.webscrapper.dto.StatisticsPerFlat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class WebScrapperController {

    private final WebScrapperService webScrapperService;

    @GetMapping("/offers")
    public ResponseEntity<List<SingleOffer>> getOffers(@RequestParam(required = false, defaultValue = "false") String isToCleanCached) {
        List<SingleOffer> content = webScrapperService.getContent(isToCleanCached);
        if (content != null && !content.isEmpty()) {
            return ResponseEntity.ok(content);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/offers/data-chart")
    public ResponseEntity<List<SingleOffer>> getSortedByOrder(@RequestParam String order, @RequestParam(required = false, defaultValue = "false") String isPricePerMc) {
        List<SingleOffer> content = webScrapperService.getSortedByOrder(order, isPricePerMc);
        if (content != null && !content.isEmpty()) {
            return ResponseEntity.ok(content);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/offers/data-chart/groups")
    public ResponseEntity<List<GroupsOffers>> getGroupsOffers(String filter, @RequestParam(required = false, defaultValue = "false") String isPricePerMc) {
        List<GroupsOffers> content = webScrapperService.getGroupsOffers(filter, isPricePerMc);
        if (content != null && !content.isEmpty()) {
            return ResponseEntity.ok(content);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/offers/data-chart/all-statistics")
    public ResponseEntity<List<Statistics>> getStatistics() {
        List<Statistics> content = webScrapperService.getStatistics();
        if (content != null && !content.isEmpty()) {
            return ResponseEntity.ok(content);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/offers/stats-per-flat")
    public ResponseEntity<StatisticsPerFlat> getStatisticsPerFlat(@RequestBody SingleOffer singleOffer) {
        return ResponseEntity.ok(webScrapperService.getStatisticsPerFlat(singleOffer));
    }


}
