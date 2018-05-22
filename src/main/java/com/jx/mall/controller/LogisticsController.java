package com.jx.mall.controller;

import com.jx.mall.entity.LogisticsRecord;
import com.jx.mall.repository.LogisticsRecordRepository;
import com.jx.mall.service.LogisticsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.jx.mall.entity.LogisticsRecord.SHIPPING;
import static com.jx.mall.entity.LogisticsRecord.SIGNED;

@RestController
@RequestMapping(value = "/logisticsRecords")
public class LogisticsController {

    @Autowired
    private LogisticsRecordRepository logisticsRepository;

    @Autowired
    private LogisticsRecordService logisticsRecordService;

    /**
     * update logisticsRecord status and order status
     *
     * @param logisticsId
     * @param orderId
     * @param logisticsStatus
     * @return
     */
    @PutMapping(value = "/{logisticsId}/orders/{orderId}")
    public ResponseEntity<String> updateLogisticsRecordStatus(@PathVariable Long logisticsId,
                                               @PathVariable Long orderId,
                                               @RequestParam String logisticsStatus) {
        if (SHIPPING.equals(logisticsStatus))
            setOutboundTimeAndLogisticsStatusToShipping(logisticsId);
        else if (SIGNED.equals(logisticsStatus))
            logisticsRecordService.signed(logisticsId, orderId);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * get logisticsRecord by id
     *
     * @param id logistics id
     * @return
     */
    @GetMapping(value = "/{id}")
    public LogisticsRecord getLogisticsRecord(@PathVariable Long id) {
        Optional<LogisticsRecord> logisticsRecordOptional = logisticsRepository.findById(id);
        if (logisticsRecordOptional.isPresent()) {
            return logisticsRecordOptional.get();
        }
        return null;
    }



    private void setOutboundTimeAndLogisticsStatusToShipping(Long logisticsId) {
        LogisticsRecord logisticsRecord = logisticsRepository.getOne(logisticsId);
        logisticsRecord.shipping();
        logisticsRepository.save(logisticsRecord);
    }
}
