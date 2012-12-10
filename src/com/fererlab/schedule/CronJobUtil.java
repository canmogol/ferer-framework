package com.fererlab.schedule;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import com.fererlab.util.XMLUtil;

public class CronJobUtil {

	private static final String GROUP_NODE = "group";
	private static final String GROUP_NAME_ATT = "name";
	private static final String JOB_NODE = "job";
	private static final String JOB_NAME_ATT = "name";
	private static final String TRIGGER_NAME_ATT = "triggerName";
	private static final String JOB_CANONICAL_NAME_ATT = "canonicalName";
	private static final String JOB_SECONDS_NODE = "seconds";
	private static final String JOB_MINUTES_NODE = "minutes";
	private static final String JOB_HOURS_NODE = "hours";
	private static final String JOB_DAY_OF_MONTH_NODE = "day-of-month";
	private static final String JOB_MONTH_NODE = "month";
	private static final String JOB_DAY_OF_WEEK_NODE = "day-of-week";
	private static final String JOB_YEAR_NODE = "year";

	private Map<String, List<CronJob>> cronJobGroups;

	private static CronJobUtil cronJobUtil;
	private String cronJobConfFile;

	private CronJobUtil(String cronJobConfFile) {

		this.cronJobConfFile = cronJobConfFile;
		cronJobGroups = new HashMap<String, List<CronJob>>();
	}

	public static synchronized CronJobUtil createInstance(String cronJobConfFile) {

		if (cronJobUtil == null) {
			cronJobUtil = new CronJobUtil(cronJobConfFile);
			cronJobUtil.loadCronJobGroups();
		}

		return cronJobUtil;
	}

	@SuppressWarnings("unchecked")
	private void loadCronJobGroups() {

		Document document = XMLUtil.getDocumentObj(this.cronJobConfFile);

		if (document != null) {
			Element root = document.getRootElement();

			List<Element> groupElements = root.getChildren(GROUP_NODE);
			CronJob cronJob = null;
			List<CronJob> cronJobs = null;
			for (Element groupElement : groupElements) {

				List<Element> jobElements = groupElement.getChildren(JOB_NODE);
				cronJobs = new ArrayList<CronJob>();
				for (Element jobElement : jobElements) {

					cronJob = new CronJob();
					cronJob.setName(jobElement.getAttributeValue(JOB_NAME_ATT));
					cronJob.setTriggerName(jobElement.getAttributeValue(TRIGGER_NAME_ATT));
					cronJob.setCanonicalName(jobElement.getAttributeValue(JOB_CANONICAL_NAME_ATT));
					cronJob.setSeconds(jobElement.getChildText(JOB_SECONDS_NODE));
					cronJob.setMinutes(jobElement.getChildText(JOB_MINUTES_NODE));
					cronJob.setHours(jobElement.getChildText(JOB_HOURS_NODE));
					cronJob.setDayOfMonth(jobElement.getChildText(JOB_DAY_OF_MONTH_NODE));
					cronJob.setMonth(jobElement.getChildText(JOB_MONTH_NODE));
					cronJob.setDayOfWeek(jobElement.getChildText(JOB_DAY_OF_WEEK_NODE));
					cronJob.setYear(jobElement.getChildText(JOB_YEAR_NODE));

					cronJobs.add(cronJob);

				}

				cronJobGroups.put(groupElement.getAttributeValue(GROUP_NAME_ATT), cronJobs);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void scheduleCronJobs() {

		for (Map.Entry<String, List<CronJob>> entry : cronJobGroups.entrySet()) {
			JobDetail job = null;
			Trigger trigger = null;
			for (CronJob cronJob : entry.getValue()) {

				try {

					job = newJob((Class<? extends Job>) Class.forName(cronJob.getCanonicalName())).withIdentity(cronJob.getName(), entry.getKey()).build();

					String jobTime = cronJob.getSeconds() + " " + cronJob.getMinutes() + " " + cronJob.getHours() + " " + cronJob.getDayOfMonth() + " " + cronJob.getMonth() + " "
							+ " " + cronJob.getDayOfWeek() + " " + cronJob.getYear();

					trigger = newTrigger().withIdentity(cronJob.getTriggerName(), entry.getKey()).withSchedule(cronSchedule(jobTime)).build();

				} catch (ClassNotFoundException e) {
					// TODO : log exception
					throw new RuntimeException(e);
				}

				ScheduleManager.scheduleJob(job, trigger);
			}
		}
	}
}
