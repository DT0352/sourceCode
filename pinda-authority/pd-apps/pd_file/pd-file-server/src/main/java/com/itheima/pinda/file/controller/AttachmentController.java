package com.itheima.pinda.file.controller;

import com.itheima.pinda.base.BaseController;
import com.itheima.pinda.base.R;
import com.itheima.pinda.exception.code.ExceptionCode;
import com.itheima.pinda.file.dto.AttachmentDTO;
import com.itheima.pinda.file.dto.AttachmentRemoveDTO;
import com.itheima.pinda.file.service.AttachmentService;
import com.itheima.pinda.utils.BizAssert;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static com.itheima.pinda.exception.code.ExceptionCode.BASE_VALID_PARAM;

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
            @RequestParam(value = "bizId", required = false)
                    String bizId,
            @RequestParam(value = "bizType", required = false)
                    String bizType,
            @RequestParam(value = "id", required = false)
                    Long id,
            @RequestParam(value = "isSingle", required = false, defaultValue = "false")
                    Boolean isSingle) {
        if (multipartFile.isEmpty()) {
            return this.fail(BASE_VALID_PARAM.build("文件无效"));
        }
        AttachmentDTO attachmentDTO = attachmentService.upload(multipartFile, bizId, bizType, id, isSingle);
        return this.success(attachmentDTO);


    }

    @ApiOperation(value = "附件删除", notes = "附件删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "文件ids", dataType = "array", paramType = "query")})
    @DeleteMapping("/delete")
    public R<Boolean> remove(@RequestParam("ids") Long[] ids) {
        attachmentService.remove(ids);
        return this.success(true);
        ///
    }

    @ApiOperation(value = " 根据业务类型或业务ID删除附件", notes = "根据业务类型或业务ID删除附件")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "文件ids", dataType = "array", paramType = "query")})
    @DeleteMapping("/biz")
    public R<Boolean> remove(@RequestBody AttachmentRemoveDTO dto) {
        attachmentService.removeByBizIdAndBizType(dto.getBizId(), dto.getBizType());
        return this.success(true);
        ///
    }

    @ApiOperation(value = "根据文件id打包下载附件", notes = "根据文件id打包下载附件")
    @GetMapping(value = "/download" )
    public void download(
            @ApiParam(name = "ids", value = "文件id数组")
            @RequestParam(value = "ids",required = false) Long[] ids,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        BizAssert.isTrue(ArrayUtils.isNotEmpty(ids),
                BASE_VALID_PARAM.build("附件id不能为空"));
        attachmentService.down(ids,request,response);
    }
}


