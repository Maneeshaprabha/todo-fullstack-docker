"use client";

import { useState, useEffect } from 'react';
import styles from "./styles/Home.module.css";

type Task = {
  id: number;
  title: string;
  description?: string;
  completed?: boolean;
};

export default function Home() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/tasks')
      .then(res => res.json())
      .then(data => setTasks(data))
      .catch(error => console.error('Error fetching tasks:', error));
  }, []);

  const addTask = async () => {
    if (!title.trim()) {
      alert('Title cannot be empty');
      return;
    }
    const response = await fetch('http://localhost:8080/tasks', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title, description }),
    });
    const task: Task = await response.json();
    setTasks(prevTasks => [task, ...prevTasks].slice(0, 5));
    setTitle('');
    setDescription('');
  };

  const markDone = async (id: number) => {
    await fetch(`http://localhost:8080/tasks/${id}`, { method: 'PUT' });
    setTasks(tasks.filter(task => task.id !== id));
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>To-Do List</h1>
      <div className={styles.taskForm}>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Task Title"
          className={styles.input}
        />
        <input
          type="text"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="Task Description"
          className={styles.input}
        />
        <button onClick={addTask} className={styles.button}>
          Add Task
        </button>
      </div>
      <div className={styles.taskList}>
        {tasks.map(task => (
          <div key={task.id} className={styles.taskCard}>
            <h3 className={styles.taskTitle}>{task.title}</h3>
            <p className={styles.taskDescription}>{task.description || 'No description'}</p>
            <button onClick={() => markDone(task.id)} className={styles.doneButton}>
              Done
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
