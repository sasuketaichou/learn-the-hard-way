package com.example.amieruljapri.myapplication27;

import java.util.List;

/**
 * Created by amierul.japri on 1/10/2017.
 */

 public class PojoListTicketsValues {

    public String id;
    public String entitiesId;
    public String name;
    public String date;
    public String closedate;
    public Object solvedate;
    public String dateMod;
    public String usersIdLastupdater;
    public String status;
    public String usersIdRecipient;
    public String requesttypesId;
    public String content;
    public String urgency;
    public String impact;
    public String priority;
    public String itilcategoriesId;
    public String type;
    public String solutiontypesId;
    public String solution;
    public String globalValidation;
    public String slasId;
    public String slalevelsId;
    public Object dueDate;
    public String beginWaitingDate;
    public String slaWaitingDuration;
    public String waitingDuration;
    public String closeDelayStat;
    public String solveDelayStat;
    public String takeintoaccountDelayStat;
    public String actiontime;
    public String isDeleted;
    public String locationsId;
    public String validationPercent;
    public Users users;
    public Groups groups;
    public String statusName;
    public String urgencyName;
    public String impactName;
    public String priorityName;
    public String usersNameRecipient;
    public String entitiesName;
    public String suppliersNameAssign;
    public String ticketcategoriesName;
    public String requesttypesName;
    public String solutiontypesName;
    public String slasName;
    public String slalevelsName;
    public String globalValidationName;


    public class Users {

        public List<Requester> requester = null;
        public List<Observer> observer = null;
        public List<Assign> assign = null;

    }

    public class Groups {

        public List<Requester_> requester = null;
        public List<Observer_> observer = null;
        public List<Assign_> assign = null;

    }

    public class Observer {

        public String id;
        public String name;

    }

    public class Observer_ {

        public String id;
        public String name;

    }

    public class Requester {

        public String id;
        public String name;

    }

    public class Assign {

        public String id;
        public String name;

    }

    public class Assign_ {

        public String id;
        public String name;

    }

    public class Requester_ {

        public String id;
        public String name;

    }

}



