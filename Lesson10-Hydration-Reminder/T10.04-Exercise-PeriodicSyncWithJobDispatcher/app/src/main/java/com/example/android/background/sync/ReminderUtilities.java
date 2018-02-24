/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;


import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class ReminderUtilities {
    // TODO (15) Create three constants and one variable:
    //  - REMINDER_INTERVAL_SECONDS should be an integer constant storing the number of seconds in 15 minutes
    //  - SYNC_FLEXTIME_SECONDS should also be an integer constant storing the number of seconds in 15 minutes
    //  - REMINDER_JOB_TAG should be a String constant, storing something like "hydration_reminder_tag"
    //  - sInitialized should be a private static boolean variable which will store whether the job
    //    has been activated or not
    final int REMINDER_INTERVAL_SECONDS = 15;
    final int SYNC_FLEXTIME_SECONDS = 15;
    final String REMINDER_JOB_TAG = "hydration_reminder_tag";
    private static boolean sInitialized = false;

    public synchronized static void scheduleChargingReminder(Context context) {

        if (sInitialized == true) {
            return;
        }

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Log.e("AMIT", "Scheduling..");

        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(WaterReminderFirebaseJobService.class)
                // uniquely identifies the job
                .setTag("REMINDER_JOB_TAG")
                // one-off job
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0, 15))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network
                        Constraint.ON_UNMETERED_NETWORK,
                        // only run when the device is charging
                        Constraint.DEVICE_CHARGING
                )
                .build();

        dispatcher.mustSchedule(myJob);
        sInitialized = true;
    }

    // TODO (16) Create a synchronized, public static method called scheduleChargingReminder that takes
    // in a context. This method will use FirebaseJobDispatcher to schedule a job that repeats roughly
    // every REMINDER_INTERVAL_SECONDS when the phone is charging. It will trigger WaterReminderFirebaseJobService
    // Checkout https://github.com/firebase/firebase-jobdispatcher-android for an example
        // TODO (17) If the job has already been initialized, return
        // TODO (18) Create a new GooglePlayDriver
        // TODO (19) Create a new FirebaseJobDispatcher with the driver
        // TODO (20) Use FirebaseJobDispatcher's newJobBuilder method to build a job which:
            // - has WaterReminderFirebaseJobService as it's service
            // - has the tag REMINDER_JOB_TAG
            // - only triggers if the device is charging
            // - has the lifetime of the job as forever
            // - has the job recur
            // - occurs every 15 minutes with a window of 15 minutes. You can do this using a
            //   setTrigger, passing in a Trigger.executionWindow
            // - replaces the current job if it's already running
        // Finally, you should build the job.
        // TODO (21) Use dispatcher's schedule method to schedule the job
        // TODO (22) Set sInitialized to true to mark that we're done setting up the job
}
