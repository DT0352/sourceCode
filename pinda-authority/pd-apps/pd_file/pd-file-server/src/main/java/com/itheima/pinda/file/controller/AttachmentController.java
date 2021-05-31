package com.itheima.pinda.file.controller;

import com.itheima.pinda.base.BaseController;
import com.itheima.pinda.base.R;
import com.itheima.pinda.exception.code.ExceptionCode;
import com.itheima.pinda.file.dto.AttachmentDTO;
import com.itheima.pinda.file.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/30
 */
@RestController
@Api(value = "附件", tags = "附件")
@RequestMapping("/attachment")
@Slf4j
public class AttachmentController extends BaseController {
    @Autowired
    private AttachmentService attachmentService;

    @ApiOperation(value = "附件上传", notes = "附件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSingle", value = "是否单文件", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "文件id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "bizId", value = "业务id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "bizType", value = "业务类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = true, required = true),
    })
    @PostMapping(value = "/upload")
    public R<AttachmentDTO> upload(
            @RequestParam(value = "file")
                    MultipartFile multipartFile,
            @RequestParam(value = "bizId",required = false)
                    String bizId,
            @RequestParam(value = "bizType",required = false)
                    String bizType,
            @RequestParam(value = "id", required = false)
                    Long id,
            @RequestParam(value = "isSingle",required = false,defaultValue = "false")
                    Boolean isSingle) {
        if (multipartFile.isEmpty()) {
            return this.fail(ExceptionCode.BASE_VALID_PARAM.build("文件无效"));
        }
        AttachmentDTO attachmentDTO = attachmentService.upload(multipartFile,bizId,bizType,id,isSingle);
        return this.success(attachmentDTO);
    }




}


