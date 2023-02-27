package com.lening.hospital.controller;


import com.lening.hospital.mapper.HospitalSetMapper;
import com.lening.hospital.model.HospitalSet;
import com.lening.hospital.service.ApiService;
import com.lening.hospital.util.YyghException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 */
@Api(tags = "医院管理接口")
@Controller
@RequestMapping
public class ApiController extends BaseController {

	@Autowired
	private ApiService apiService;

	/**
	 * Autowired一般注入的时候，他是直接找代理的实现，要是这个接口没有实现类，就给红了
	 */
	@Autowired(required = false)
	private HospitalSetMapper hospitalSetMapper;


	/**
	 * ModelMap：
	 * Model
	 * ModelAndView
	 *
	 */
	@RequestMapping("/hospitalSet/index")
	public String getHospitalSet(ModelMap model,RedirectAttributes redirectAttributes) {
		HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
		model.addAttribute("hospitalSet", hospitalSet);
		return "hospitalSet/index";
	}

	@RequestMapping(value="/hospitalSet/save")
	public String createHospitalSet(ModelMap model,HospitalSet hospitalSet) {
		hospitalSetMapper.updateById(hospitalSet);
		return "redirect:/hospitalSet/index";
	}

	@RequestMapping("/hospital/index")
	public String getHospital(ModelMap model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			/**
			 * 第一步先查询医院设置信息，先看看有没有医院编码和前面的key要是没有，这个查询就没有意义
			 * 打会到医院设置里面了，医院没有资格在平台去查询，信息不全
			 */
			HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
			if(null == hospitalSet || StringUtils.isEmpty(hospitalSet.getHoscode()) || StringUtils.isEmpty(hospitalSet.getSignKey())) {
				this.failureMessage("先设置医院code与签名key", redirectAttributes);
				return "redirect:/hospitalSet/index";
			}

			/**
			 * 要是一样的code和key有的话，去查询
			 * apiService.getHospital()，抓这个方法（跟踪一下）
			 */
			model.addAttribute("hospital", apiService.getHospital());
		} catch (YyghException e) {
			this.failureMessage(e.getMessage(), request);
		} catch (Exception e) {
			this.failureMessage("数据异常", request);
		}
		return "hospital/index";
	}

	@RequestMapping(value="/hospital/create")
	public String createHospital(ModelMap model) {
		return "hospital/create";
	}

	@RequestMapping(value="/hospital/save",method=RequestMethod.POST)
	public String saveHospital(String data, HttpServletRequest request) {
		try {
			apiService.saveHospital(data);
		} catch (YyghException e) {
			return this.failurePage(e.getMessage(),request);
		} catch (Exception e) {
			return this.failurePage("数据异常",request);
		}
		return this.successPage(null,request);
	}

	//科室的查询
	@RequestMapping("/department/list")
	public String findDepartment(ModelMap model,
								 @RequestParam(defaultValue = "1") int pageNum,
								 @RequestParam(defaultValue = "10") int pageSize,
								 HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
			if(null == hospitalSet || StringUtils.isEmpty(hospitalSet.getHoscode()) || StringUtils.isEmpty(hospitalSet.getSignKey())) {
				this.failureMessage("先设置医院code与签名key", redirectAttributes);
				return "redirect:/hospitalSet/index";
			}

			model.addAllAttributes(apiService.findDepartment(pageNum, pageSize));
		} catch (YyghException e) {
			this.failureMessage(e.getMessage(), request);
		} catch (Exception e) {
			this.failureMessage("数据异常", request);
		}
		return "department/index";
	}

	@RequestMapping(value="/department/create")
	public String create(ModelMap model) {
		return "department/create";
	}

	@RequestMapping(value="/department/save",method=RequestMethod.POST)
	public String save(String data, HttpServletRequest request) {
		try {
			apiService.saveDepartment(data);
		} catch (YyghException e) {
			return this.failurePage(e.getMessage(),request);
		} catch (Exception e) {
			return this.failurePage("数据异常",request);
		}
		return this.successPage(null,request);
	}

	@RequestMapping("/schedule/list")
	public String findSchedule(ModelMap model,
								 @RequestParam(defaultValue = "1") int pageNum,
								 @RequestParam(defaultValue = "10") int pageSize,
							   HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
			if(null == hospitalSet || StringUtils.isEmpty(hospitalSet.getHoscode()) || StringUtils.isEmpty(hospitalSet.getSignKey())) {
				this.failureMessage("先设置医院code与签名key", redirectAttributes);
				return "redirect:/hospitalSet/index";
			}

			model.addAllAttributes(apiService.findSchedule(pageNum, pageSize));
		} catch (YyghException e) {
			this.failureMessage(e.getMessage(), request);
		} catch (Exception e) {
			this.failureMessage("数据异常", request);
		}
		return "schedule/index";
	}

	@RequestMapping(value="/schedule/create")
	public String createSchedule(ModelMap model) {
		return "schedule/create";
	}

	@RequestMapping(value="/schedule/save",method=RequestMethod.POST)
	public String saveSchedule(String data, HttpServletRequest request) {
		try {
			//data = data.replaceAll("\r\n", "").replace(" ", "");
			apiService.saveSchedule(data);
		} catch (YyghException e) {
			return this.failurePage(e.getMessage(),request);
		} catch (Exception e) {
			e.printStackTrace();
			return this.failurePage("数据异常："+e.getMessage(),request);
		}
		return this.successPage(null,request);
	}

	@RequestMapping(value="/hospital/createBatch")
	public String createHospitalBatch(ModelMap model) {
		return "hospital/createBatch";
	}

	@RequestMapping(value="/hospital/saveBatch",method=RequestMethod.POST)
	public String saveBatchHospital(HttpServletRequest request) {
		try {
			apiService.saveBatchHospital();
		} catch (YyghException e) {
			return this.failurePage(e.getMessage(),request);
		} catch (Exception e) {
			return this.failurePage("数据异常",request);
		}
		return this.successPage(null,request);
	}

	@RequestMapping(value="/department/remove/{depcode}",method=RequestMethod.GET)
	public String removeDepartment(ModelMap model, @PathVariable String depcode, RedirectAttributes redirectAttributes) {
		apiService.removeDepartment(depcode);

		this.successMessage(null, redirectAttributes);
		return "redirect:/department/list";
	}

	@RequestMapping(value="/schedule/remove/{hosScheduleId}",method=RequestMethod.GET)
	public String removeSchedule(ModelMap model, @PathVariable String hosScheduleId, RedirectAttributes redirectAttributes) {
		apiService.removeSchedule(hosScheduleId);

		this.successMessage(null, redirectAttributes);
		return "redirect:/schedule/list";
	}

}

