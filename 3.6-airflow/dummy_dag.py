from airflow import DAG
from airflow.operators.dummy_operator import DummyOperator
from airflow.operators.python_operator import PythonOperator
from airflow.operators.python import BranchPythonOperator
from airflow.decorators import task

from datetime import datetime
from random import randint
import os.path
from os import system


def my_func():
    """
    Extract text from files of a given directory
    :param input_dir: path to source directory
    :param output_dir: path to output directory
    """

    print('Hello from my_func')


def summator():
    """
                     a. Создайте еще один PythonOperator, который генерирует два произвольных числа и печатает их.
                    Добавьте вызов нового оператора в конец вашего pipeline с помощью >>.
    """
    a, b = _get_randon_ints_pair()
    print(f" {a} + {b} = {a+b}")


def _get_randon_ints_pair():
    a = randint(0, 100)
    b = randint(0, 100)

    return (a, b)


def get_nums(str):
    res = []
    for s in str.split(" "):
        s.strip()
        if len(s) > 0:
            res.append(int(s))
    return tuple(res) if len(res) == 2 else None


def file_summator(fname="./result_dummy_dag.txt"):
    """
    Создайте новый оператор, который подключается к файлу и вычисляет сумму всех чисел из первой колонки,
    затем сумму всех чисел из второй колонки и рассчитывает разность полученных сумм.
    Вычисленную разность необходимо записать в конец того же файла, не затирая его содержимого.
    """
    print(f"file_summator - file {fname} - exists {os.path.isfile(fname)}")
    if os.path.isfile(fname):
        with open(fname, 'r+', encoding='utf-8') as f:
            num_pairs = []
            for ln in f.readlines():
                num_pair = get_nums(ln)

                if num_pair is None:
                    continue
                num_pairs.append(num_pair)

            sumA = sum([nums[0] for nums in num_pairs])
            sumB = sum([nums[1] for nums in num_pairs])
            difference = sumA - sumB
            print(f"sumA - sumB = {difference}")
            f.write(f"{difference}\n")


def write_2_ints_to_file(fname="./result_dummy_dag.txt"):
    """
    сгенерированные числа должны записываться в текстовый файл – через пробел.
    При этом должно соблюдаться условие, что каждые новые два числа должны записываться
                    с новой строки не затирая предыдущие.
    """
    a = randint(0, 100)
    b = randint(0, 100)

    print(
        f"write_2_ints_to_file - file {fname} - exists {os.path.isfile(fname)}")

    if os.path.isfile(fname):
        with open(fname, 'a', encoding='utf-8') as f:
            a, b = _get_randon_ints_pair()
            f.write(f"{a} {b}\n")
    else:
        with open(fname, 'w', encoding='utf-8') as f:
            a, b = _get_randon_ints_pair()
            f.write(f"{a} {b}\n")
    print(
        f"write_2_ints_to_file - end - file {fname} - exists {os.path.isfile(fname)}")

# def write_2_ints_to_file_mod(fname="./result_dummy_dag.txt"):
# 	"""
# 	 Измените еще раз логику вашего оператора из пунктов 12.а – 12.с.
#    При каждой новой записи произвольных чисел в конец файла,
#    вычисленная сумма на шаге 12.d должна затираться.
# 	"""
# 	a = randint(0, 100)
# 	b = randint(0, 100)

# 	print(f"write_2_ints_to_file - file {fname} - exists {os.path.isfile(fname)}")

# 	if os.path.isfile(fname):
# 		with open(fname, 'a', encoding='utf-8') as f:
# 			a, b = _get_randon_ints_pair()
# 			f.write(f"{a} {b}\n")
# 	else:
# 		with open(fname, 'w', encoding='utf-8') as f:
# 			a, b = _get_randon_ints_pair()
# 			f.write(f"{a} {b}\n")
# 	print(f"write_2_ints_to_file - end - file {fname} - exists {os.path.isfile(fname)}")


def remove_last_string(fname="./result_dummy_dag.txt"):
    system('sed -i "$ d" {0}'.format(fname))


def check_last_string(fname="./result_dummy_dag.txt"):

    if os.path.isfile(fname):
        with open(fname, encoding='utf-8') as f:
            line = ""
            for line in f:
                pass
            last_line = line

            nums = last_line.strip().split(" ")
            if len(nums) == 1:
                print(f"last string contains {nums} - remove it")
                return "remove_last_string_task"
            else:
                print(f"last string contains {nums} - add 2 integers")
                return "write_2_ints_to_file_task"

def print_file(fname="./result_dummy_dag.txt"):
    if os.path.isfile(fname):
        with open(fname, encoding='utf-8') as f:
            print(f"{ f.readlines()}")
    



# b. Воспользоваться официальным образом Airflow для Docker: https://airflow.apache.org/docs/docker-stack/index.html
# a. dag_id – уникальное имя dag’a, не должно повторяться у других dag’ов
#  b. start_date – дата начала работы нашего DAG’a
#  c. schedule – настройка интервала запуска DAG’а.( один раз каждые сутки. )
with DAG('dummy-dag', description='Dummy DAG', schedule_interval='* * * * *', start_date=datetime(2022, 6, 12), catchup=False, max_active_runs=5) as dag:

    # @task.branch(task_id="check_last_string_task")

    dummy_oper = DummyOperator(task_id='dummy_task', retries=3)

    write_2_ints_to_file_oper = PythonOperator(
        task_id='write_2_ints_to_file_task', python_callable=write_2_ints_to_file)

    file_summator_oper = PythonOperator(
        task_id='file_summator_task', python_callable=file_summator, trigger_rule="none_failed_or_skipped")

    # check_last_string_task = PythonOperator(
    # 	task_id='check_last_string_task', python_callable=remove_last_string)

    remove_last_string_oper = PythonOperator(
        task_id='remove_last_string_task', python_callable=remove_last_string)

    check_last_string_oper = BranchPythonOperator(
        task_id='check_last_string_task', python_callable=check_last_string)

    print_file_oper = PythonOperator(
        task_id='print_file_task', python_callable=print_file)


    dummy_oper >> check_last_string_oper >> [remove_last_string_oper, write_2_ints_to_file_oper] >> file_summator_oper >> print_file_oper
    
