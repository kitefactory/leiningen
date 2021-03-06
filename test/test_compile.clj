(ns test-compile
  (:use [clojure.test]
        [clojure.contrib.io :only [delete-file-recursively file]]
        [clojure.contrib.shell :only [with-sh-dir sh]]))

(deftest test-compile
  (delete-file-recursively (file "test_projects" "sample" "classes" "nom") true)
  (with-sh-dir (file "test_projects" "sample")
    (is (zero? (:exit (sh "lein" "compile" :return-map true)))))
  (is (.exists (file "test_projects" "sample"
                     "classes" "nom" "nom" "nom.class")))
  (with-sh-dir (file "test_projects" "sample_failing")
    (is (not (zero? (:exit (sh "lein" "compile" :return-map true)))))))
